const GO_LIVE_REGULATION_URL="/regulation/go-live/United+Kingdom";
const INCOMING_REGULATION_URL="/regulation/incoming/220";
const AT_RISK_BUSINESS_URL="/business/at-risk/";
const UNIQUE_SUPERVISORY_BODIES_URL="/regulation/no-supervisory-bodies/FCA";

class DashboardServices{

    static async fetchDataGET(URL) {
        const getRequest = {
            method: "GET",
            mode: "cors",
            headers: {'Content-Type': 'application/json'}
        };
        try {
            let response = await fetch(URL,  {...getRequest});
            return await response.json()
        }catch(e){
            console.log(e)
        }

    };

    static getUniqueSupervisorBodyCount(){
        return this.fetchDataGET(UNIQUE_SUPERVISORY_BODIES_URL);
    };

    static getIncomingRegulations(){
        return this.fetchDataGET(INCOMING_REGULATION_URL);
    };

    static getGoingLiveRegulations(){
        return this.fetchDataGET(GO_LIVE_REGULATION_URL);
    };

    static getAtRiskBusinessUnits(){
        return this.fetchDataGET(AT_RISK_BUSINESS_URL);
    };
}

export default DashboardServices;
