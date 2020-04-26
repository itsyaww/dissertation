const BASE_URL="http://backend-env.eba-tykhr4mp.eu-west-2.elasticbeanstalk.com:8080";
const GO_LIVE_REGULATION_URL= BASE_URL + "/regulation/incoming/";
const INCOMING_REGULATION_URL=BASE_URL + "/regulation/incoming/";
const GET_ALL_REGULATION_URL=BASE_URL + "/regulation/";
const AT_RISK_BUSINESS_URL=BASE_URL + "/business-unit/at-risk/";
const UNIQUE_SUPERVISORY_BODIES_URL=BASE_URL + "/handbook/count/";

class DashboardServices{

    static async fetchDataGET(URL) {
        const getRequest = {
            method: "GET",
            mode: "cors",
            headers: {'Content-Type': 'application/json'}
        };
        try {
            let response = await fetch(URL,  {...getRequest});
            return await response.json();
        }catch(e){
            console.log(e);
        }

    };

    static getUniqueSupervisorBodyCount(){
        return this.fetchDataGET(UNIQUE_SUPERVISORY_BODIES_URL);
    };

    static getIncomingRegulations(){
        return this.fetchDataGET(INCOMING_REGULATION_URL);
    };

    static getAllRegulations(){
        return this.fetchDataGET(GET_ALL_REGULATION_URL);
    };

    static getGoingLiveRegulations(){
        return this.fetchDataGET(GO_LIVE_REGULATION_URL);
    };

    static getAtRiskBusinessUnits(){
        return this.fetchDataGET(AT_RISK_BUSINESS_URL);
    };
}

export default DashboardServices;