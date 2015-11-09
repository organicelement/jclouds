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

import com.google.auto.service.AutoService;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Module;
import com.google.inject.name.Named;
import org.jclouds.apis.ApiMetadata;
import org.jclouds.hpcloud.objectstorage.v1.config.HPCloudObjectStorageHttpApiModule;
import org.jclouds.location.suppliers.RegionIdToURISupplier;
import org.jclouds.openstack.keystone.v2_0.config.AuthenticationApiModule;
import org.jclouds.openstack.keystone.v2_0.config.CredentialTypes;
import org.jclouds.openstack.keystone.v2_0.config.KeystoneAuthenticationModule.RegionModule;
import org.jclouds.openstack.keystone.v2_0.suppliers.RegionIdToAdminURISupplier;
import org.jclouds.openstack.swift.v1.SwiftApiMetadata;
import org.jclouds.openstack.swift.v1.blobstore.RegionScopedBlobStoreContext;
import org.jclouds.openstack.swift.v1.blobstore.config.SignUsingTemporaryUrls;
import org.jclouds.openstack.swift.v1.config.SwiftTypeAdapters;
import org.jclouds.openstack.v2_0.ServiceType;
import org.jclouds.rest.annotations.ApiVersion;
import org.jclouds.rest.internal.BaseHttpApiMetadata;

import java.net.URI;
import java.util.Properties;

import static org.jclouds.openstack.keystone.v2_0.config.KeystoneProperties.CREDENTIAL_TYPE;
import static org.jclouds.openstack.keystone.v2_0.config.KeystoneProperties.SERVICE_TYPE;
import static org.jclouds.reflect.Reflection2.typeToken;

/**
 * Implementation of {@link ApiMetadata} for HP Cloud Object Storage.
 */
@AutoService(ApiMetadata.class)
public class HPCloudObjectStorageApiMetadata extends BaseHttpApiMetadata<HPCloudObjectStorageApi> {

   @Override
   public Builder toBuilder() {
      return new Builder().fromApiMetadata(this);
   }

   public HPCloudObjectStorageApiMetadata() {
      this(new Builder());
   }

   protected HPCloudObjectStorageApiMetadata(Builder builder) {
      super(builder);
   }

   public static Properties defaultProperties() {
      Properties properties = SwiftApiMetadata.defaultProperties();
      properties.setProperty(CREDENTIAL_TYPE, CredentialTypes.API_ACCESS_KEY_CREDENTIALS);
      properties.setProperty(SERVICE_TYPE, ServiceType.OBJECT_STORE);
      return properties;
   }

   public static class Builder extends BaseHttpApiMetadata.Builder<HPCloudObjectStorageApi, Builder> {

      protected Builder() {
          id("hpcloud-objectstorage")
         .name("HP Cloud Object Storage API")
         .identityName("${userName}")
         .credentialName("${apiKey}")
         .documentation(URI.create("https://build.hpcloud.com/object-storage/api"))
         .version("1.0")
         .endpointName("identity service URL ending in /v2.0/")
         .defaultEndpoint("https://region-a.geo-1.identity.hpcloudsvc.com:35357/v2.0/")
         .defaultProperties(HPCloudObjectStorageApiMetadata.defaultProperties())
         .view(typeToken(RegionScopedBlobStoreContext.class))
                  .defaultModules(ImmutableSet.<Class<? extends Module>>builder()
                          .add(AuthenticationApiModule.class)
//                          .add(HPCloudObjectStorageEndpointModule.class)
                          .add(IgnoreRegionVersionsModule.class)
//                          .add(RegionModule.class)
                          .add(SwiftTypeAdapters.class)
                          .add(HPCloudObjectStorageHttpApiModule.class)
//                          .add(HPCloudObjectStorageBlobStoreContextModule.class)
//See below
//                                     .add(HPCloudObjectStorageTemporaryUrlExtensionModule.class)
                          .add(SignUsingTemporaryUrls.class)
                          .build());
      }

      @Override
      public HPCloudObjectStorageApiMetadata build() {
         return new HPCloudObjectStorageApiMetadata(this);
      }

      @Override
      protected Builder self() {
         return this;
      }
   }

//   This might not be necessary, check functionality of SignUsingTemporaryUrls.class
   /**
    * Ensures keystone auth is used instead of swift auth
    */
/*
   public static class HPCloudObjectStorageTemporaryUrlExtensionModule extends
         TemporaryUrlExtensionModule<HPCloudObjectStorageApi> {
      @Override
      protected void bindRequestSigner() {
         bind(BlobRequestSigner.class).to(HPCloudObjectStorageBlobRequestSigner.class);
      }
      @Override
      protected void bindTemporaryUrlKeyApi() {
         bindHttpApi(binder(), KeystoneTemporaryUrlKeyApi.class);
         bind(TemporaryUrlKeyApi.class).to(KeystoneTemporaryUrlKeyApi.class);
      }
   }
*/

   /**
    * Use this when the keystone configuration incorrectly mismatches api
    * versions across regions.
    */
   public static class IgnoreRegionVersionsModule extends RegionModule {

      @Override
      protected RegionIdToURISupplier provideRegionIdToURISupplierForApiVersion(
            @Named(SERVICE_TYPE) String serviceType, @ApiVersion String apiVersion,
            RegionIdToURISupplier.Factory factory) {
         return factory.createForApiTypeAndVersion(serviceType, null);
      }

      @Override
      protected RegionIdToAdminURISupplier provideRegionIdToAdminURISupplierForApiVersion(
            @Named(SERVICE_TYPE) String serviceType, @ApiVersion String apiVersion,
            RegionIdToAdminURISupplier.Factory factory) {
         return factory.createForApiTypeAndVersion(serviceType, null);
      }
   }

}
