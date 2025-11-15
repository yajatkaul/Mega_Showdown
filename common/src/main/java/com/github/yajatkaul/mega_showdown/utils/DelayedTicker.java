package com.github.yajatkaul.mega_showdown.utils;

import java.util.LinkedList;
import java.util.List;

public abstract class DelayedTicker {
    private static final List<DelayedTicker> tickers = new LinkedList<>();

    protected final int maxAge;
    protected int age;

    public DelayedTicker(int maxAge) {
        this.age = 0;
        this.maxAge = maxAge;
    }

    public static void add(DelayedTicker ticker) {
        tickers.add(ticker);
    }

    public static void runAll() {
        tickers.removeIf(DelayedTicker::isDone);
        tickers.forEach(DelayedTicker::run);
    }

    public void run() {
        this.function();
        ++this.age;
    }

    public boolean isDone() {
        return this.age > this.maxAge;
    }

    protected abstract void function();
}
