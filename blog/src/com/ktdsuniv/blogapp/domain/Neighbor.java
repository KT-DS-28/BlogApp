package com.ktdsuniv.blogapp.domain;

import com.ktdsuniv.blogapp.enums.NeighborState;

public class Neighbor {

    private final User requester;
    private final User receiver;
    private NeighborState state = NeighborState.PENDING;

    public Neighbor(User requester, User receiver) {
        this.requester = requester;
        this.receiver = receiver;
    }

    public User getOther(User user) {
        if (this.requester == user) {
            return this.receiver;
        } else {
            return this.requester;
        }
    }

    public User getRequester() {
        return this.requester;
    }

    public User getReceiver() {
        return this.receiver;
    }

    public NeighborState getState() {
        return this.state;
    }

    public void setState(NeighborState state) {
        this.state = state;
    }

}
