package com.example.Sample.SpringTest.grpc;

import MyPackage.Response;
import io.grpc.stub.StreamObserver;

public class MyResponseObserver implements StreamObserver<Response> {
    @Override
    public void onNext(Response response) {
        // Handle the response
        System.out.println("Received response: " + response.getResult());
    }

    @Override
    public void onError(Throwable t) {
        // Handle errors
        t.printStackTrace();
    }

    @Override
    public void onCompleted() {
        // Handle completion
        System.out.println("Response stream completed.");
    }
}
