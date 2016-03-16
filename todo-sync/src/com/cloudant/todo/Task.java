/**
 * Copyright (c) 2015 Cloudant, Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */

package com.cloudant.todo;

import com.cloudant.sync.datastore.BasicDocumentRevision;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/*
 * Object representing a task.
 *
 * As well as acting as a value object, this class also has a reference to the original
 * DocumentRevision, which will be valid if the Task was fetched from the database, or else null
 * (eg for Tasks which have been created but not yet saved to the database).
 *
 * fromRevision() and asMap() act as helpers to map to and from JSON - in a real application
 * something more complex like an object mapper might be used.
 */

public class Task {

    // this is the revision in the database representing this task
    private BasicDocumentRevision rev;
    static final String DOC_TYPE = "Feature";
    private String type = DOC_TYPE;
    private String geometry = "Point";
    private String description;
    double longitude ;//= location.getLongitude();
    double latitude ;//= location.getLatitude();

    private Task() {}

    public Task(String desc, SimpleLocation location) {
        this.setDescription(desc);
        this.setType(DOC_TYPE);
        this.setLongitude(location.getLongitude());
        this.setLatitude(location.getLatitude());
    }


    public BasicDocumentRevision getDocumentRevision() {
        return rev;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }


    public String getGeometry() {
        return geometry;
    }
    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }

    public String getDescription() {
        return this.description;
    }
    public void setDescription(String desc) {
        this.description = desc;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


    @Override
    public String toString() {
        return "{ desc: " + getDescription() + ", completed: " + /*isCompleted() +*/ "}";
    }


    public static Task fromRevision(BasicDocumentRevision rev) {
        Task t = new Task();
        t.rev = rev;
        // this could also be done by a fancy object mapper
        Map<String, Object> map = rev.asMap();
        if(map.containsKey("tipe") && map.get("tipe").equals(Task.DOC_TYPE)) {
            t.setType((String) map.get("tipe"));
            t.setGeometry((String) map.get("geometry"));
            //t.setLatitude((Double) map.get("latitude"));
            //t.setLongitude((Double) map.get("longitude"));
            return t;
        }
        return null;
    }

    public Map<String, Object> asMap() {
        // this could also be done by a fancy object mapper
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("tipe", type);
        map.put("geometry", geometry);
        map.put("latitude", latitude);
        map.put("longitude", longitude);
        map.put("description", description);
        return map;
    }

}
