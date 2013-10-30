package com.mycompany;
/*
 * Copyright 2013 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *
 * @author <a href="http://tfox.org">Tim Fox</a>
 */

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.platform.Verticle;

/*
This is a simple Java verticle which receives `ping` messages on the event bus and sends back `pong` replies
 */
public class PingVerticle extends Verticle {

  public void start() {


    vertx.eventBus().registerHandler("ping-address", new Handler<Message<String>>() {
      @Override
      public void handle(Message<String> message) {
        message.reply("pong!");
        container.logger().info("Sent back pong");
      }
    });

    JsonObject config1 = new JsonObject().putString("address", "wqaddr1");

    container.deployModule("io.vertx~mod-work-queue~2.0.0-final", config1, new Handler<AsyncResult<String>>() {
      @Override
      public void handle(final AsyncResult<String> event) {
        System.out.println("deployed:" + event);
      }
    });

    JsonObject config2 = new JsonObject().putString("address", "wqaddr2");

    container.deployModule("io.vertx~mod-work-queue~2.0.0-final", config2, new Handler<AsyncResult<String>>() {
      @Override
      public void handle(final AsyncResult<String> event) {
        System.out.println("deployed:" + event);
      }
    });


    container.logger().info("PingVerticle started");
    container.logger().info(" class:" + this);
    container.logger().info(" class hashcode:" + this.getClass().hashCode());
    container.logger().info(" classloader " + this.getClass().getClassLoader());

  }
}
