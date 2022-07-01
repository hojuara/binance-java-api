package com.binance.api.client.impl;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;

import com.binance.api.client.BinanceApiError;
import com.binance.api.client.constant.BinanceApiConstants;
import com.binance.api.client.exception.BinanceApiException;
import com.binance.api.client.security.AuthenticationInterceptor;

import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Generates a Binance API implementation based on @see
 * {@link BinanceApiService}.
 */
public class BinanceApiServiceGenerator {

    private static final OkHttpClient sharedClient;
    private static final Converter.Factory converterFactory = JacksonConverterFactory.create();

    static {
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequestsPerHost(500);
        dispatcher.setMaxRequests(500);
        sharedClient = new OkHttpClient.Builder()
                .dispatcher(dispatcher)
                .pingInterval(180, TimeUnit.SECONDS)
                .build();
    }

    @SuppressWarnings("unchecked")
    private static final Converter<ResponseBody, BinanceApiError> errorBodyConverter = (Converter<ResponseBody, BinanceApiError>) converterFactory.responseBodyConverter(BinanceApiError.class, new Annotation[0], null);

    public static <S> S createService(Class<S> serviceClass) {
        return createClusteredService(serviceClass, null, null);
    }

    public static <S> S createService(Class<S> serviceClass, String apiKey, String secret) {
        return createClusteredService(serviceClass, apiKey, secret);
    }

    private static <S> S createService(String baseUrl, Class<S> serviceClass, String apiKey, String secret) {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(converterFactory);

        if (StringUtils.isEmpty(apiKey) || StringUtils.isEmpty(secret)) {
            retrofitBuilder.client(sharedClient);
        } else {
            // `adaptedClient` will use its own interceptor, but share thread pool etc with
            // the 'parent' client
            AuthenticationInterceptor interceptor = new AuthenticationInterceptor(apiKey, secret);
            OkHttpClient adaptedClient = sharedClient.newBuilder().addInterceptor(interceptor).build();
            retrofitBuilder.client(adaptedClient);
        }

        Retrofit retrofit = retrofitBuilder.build();
        return retrofit.create(serviceClass);
    }

    @SuppressWarnings("unchecked")
    private static <S> S createClusteredService(Class<S> serviceClass, String apiKey, String secret) {
        Queue<S> realServices = new LinkedList<>();
        for (var baseUrl : BinanceApiConstants.API_BASE_URL_CLUSTER) {
            realServices.offer(createService(baseUrl, serviceClass, apiKey, secret));
        }
        return (S) Proxy.newProxyInstance(serviceClass.getClassLoader(), new Class[] { serviceClass }, new ClusteredApiInvocationHandler<>(realServices));
    }

    /**
     * Execute a REST call and block until the response is received.
     */
    public static <T> T executeSync(Call<T> call) {
        try {
            Response<T> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                BinanceApiError apiError = getBinanceApiError(response);
                throw new BinanceApiException(apiError);
            }
        } catch (IOException e) {
            throw new BinanceApiException(e);
        }
    }

    /**
     * Extracts and converts the response error body into an object.
     */
    public static BinanceApiError getBinanceApiError(Response<?> response) throws IOException, BinanceApiException {
        return errorBodyConverter.convert(response.errorBody());
    }

    /**
     * Returns the shared OkHttpClient instance.
     */
    public static OkHttpClient getSharedClient() {
        return sharedClient;
    }

    private static final class ClusteredApiInvocationHandler<S> implements InvocationHandler {

        private Queue<S> services;

        public ClusteredApiInvocationHandler(Queue<S> services) {
            this.services = services;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            S nextService = null;
            while ((nextService = services.poll()) == null);
            services.offer(nextService);
            return method.invoke(nextService, args);
        }
    }
}