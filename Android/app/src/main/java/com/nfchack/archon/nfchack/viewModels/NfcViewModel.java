package com.nfchack.archon.nfchack.viewModels;

import com.google.gson.Gson;
import com.nfchack.archon.nfchack.models.NfcRequest;

import rx.functions.Action1;
import rx.subjects.BehaviorSubject;

/**
 * Created by Evan on 12/21/2015.
 */
public class NfcViewModel {
    private String _request;
    public BehaviorSubject<String> Request;
    public void setRequest(String value) { _request = value; Request.onNext(value); }

    public NfcViewModel() {
        Request = BehaviorSubject.create();

    }
}
