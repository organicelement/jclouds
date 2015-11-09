/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jclouds.hpcloud.objectstorage.v1.reference;


/**
 * Constants for HP Cloud Object Storage.
 */
public final class HPCloudObjectStorageConstants {
   public static final int CDN_TTL_MIN = 900;
   public static final int CDN_TTL_MAX = 31536000;
   public static final int CDN_TTL_DEFAULT = 259200;

   private HPCloudObjectStorageConstants() {
      throw new AssertionError("intentionally unimplemented");
   }
}