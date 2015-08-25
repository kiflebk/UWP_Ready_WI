/*
*
*  Copyright 2015 University of Wisconsin - Parkside
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*
*
*/

package u.ready_wisc.Counties;

/**
 * Created by nathaneisner on 6/2/15.
 */
public class County {
    private String name;
    private String code;
    private String appID;
    private String region;

    public County(String name, String code, String appID, String region) {
        this.appID = appID;
        this.name = name;
        this.code = code;
        this.region = region;
    }

    public String getRegion() {
        return region;
    }

    public String getAppID() {
        return appID;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
