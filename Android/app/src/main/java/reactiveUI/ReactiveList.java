package reactiveUI;

import java.util.List;

import rx.subjects.BehaviorSubject;

/**
 * Created by Evan on 12/21/2015.
 */
public class ReactiveList<T> {
    private List<T> _list;
    public BehaviorSubject<T> ItemAdded;
    public BehaviorSubject<T> ItemRemoved;
    public BehaviorSubject<List<T>> Changed;

    public ReactiveList() {
        ItemAdded = BehaviorSubject.create();
        ItemRemoved = BehaviorSubject.create();
        Changed = BehaviorSubject.create();
    }

    public void add(T item) {
        _list.add(item);
        ItemAdded.onNext(item);
        Changed.onNext(_list);
    }

    public void remove(T item) {
        _list.remove(item);
        ItemRemoved.onNext(item);
        Changed.onNext(_list);
    }
}
