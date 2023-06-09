package com.example.fakestore.utilities;

import androidx.lifecycle.MutableLiveData;

import com.example.fakestore.models.StateDataModel;

public class StateLiveData<T> extends MutableLiveData<StateDataModel> {
    /**
     * Use this to put the Data on a LOADING Status
     */
    public void postLoading() {
        postValue(new StateDataModel<T>().loading());
    }

    public void setSuccess(T data){
        setValue(new StateDataModel<T>().success(data));
    }
    /**
     * Use this to put the Data on a ERROR DataStatus
     * @param throwable the error to be handled
     */
    public void postError(Throwable throwable) {
        postValue(new StateDataModel<T>().error(throwable));
    }

    /**
     * Use this to put the Data on a SUCCESS DataStatus
     * @param data passes data
     */
    public void postSuccess(T data) {
        postValue(new StateDataModel<T>().success(data));
    }

    /**
     * Use this to put the Data on a COMPLETE DataStatus
     */
    public void postComplete() {
        postValue(new StateDataModel<T>().complete());
    }
}
