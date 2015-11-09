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
package org.jclouds.hpcloud.objectstorage.v1;

import org.jclouds.openstack.swift.v1.SwiftApi;
import org.jclouds.hpcloud.objectstorage.v1.features.CDNApi;
import org.jclouds.hpcloud.objectstorage.v1.functions.RegionToCDNEndpoint;
import org.jclouds.rest.annotations.Delegate;
import org.jclouds.rest.annotations.EndpointParam;

import com.google.common.annotations.Beta;

/**
 * HP Cloud Object Storage is designed for ultra-high durability so your data is available
 * for immediate retrieval. Every object is copied three times and stored in physically separate
 * availability zones. HP Cloud Object Storage is powered by OpenStack® technology and runs on
 * high-performance HP servers. And our industry-leading service level agreement grants the world class
 * protection and support you expect from HP.
 * <p/>
 * Deliver static data from HP Cloud Object Storage to users around the world in a flash. We cache your
 * content and distribute it across HP and Akamai global networks for super fast transfer. We minimize
 * latency by ensuring that anyone in the world can access your data from a server near them.
 *
 * @see CDNApi
 * @see SwiftApi
 */
@Beta
public interface HPCloudObjectStorageApi extends SwiftApi {

   /**
    * Provides access to HP Cloud Object Storage CDN features.
    *
    * @param region  the region to access the CDN API.
    *
    * @return the {@link CDNApi} for the specified region.
    */
   @Delegate
   CDNApi getCDNApi(@EndpointParam(parser = RegionToCDNEndpoint.class) String region);

}
