package com.example.fakestore.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class StateDataModel<T> {
    @NonNull
    private DataStatus status;

    @Nullable
    private T data;

    @Nullable
    private Throwable error;

    public StateDataModel() {
        this.status = DataStatus.CREATED;
        this.data = null;
        this.error = null;
    }

    public StateDataModel<T> loading() {
        this.status = DataStatus.LOADING;
        this.data = null;
        this.error = null;
        return this;
    }

    public StateDataModel<T> success(@NonNull T data) {
        this.status = DataStatus.SUCCESS;
        this.data = data;
        this.error = null;
        return this;
    }

    public StateDataModel<T> error(@NonNull Throwable error) {
        this.status = DataStatus.ERROR;
        this.data = null;
        this.error = error;
        return this;
    }

    public StateDataModel<T> complete() {
        this.status = DataStatus.COMPLETE;
        return this;
    }

    @NonNull
    public DataStatus getStatus() {
        return status;
    }

    @Nullable
    public T getData() {
        return data;
    }

    @Nullable
    public Throwable getError() {
        return error;
    }

    public enum DataStatus {
        CREATED,
        SUCCESS,
        ERROR,
        LOADING,
        COMPLETE
    }
}
