package com.communicator;

/**
 * Rainbow live price consumer
 * Copyright (C) 2014, Greeno Tech Solutions Pvt. Ltd.
 */

/**
 * @author SELLATHURAI
 * @version 1.0, Rel 1, 1 Nov 2014
 */

public interface TaskCompleteListener<JsonRequestData> {
    void onTaskComplete(JsonRequestData jsonRequestData);
}
