const GET_ALL_BUSINESS_UNITS_URL="http://backend-env.eba-tykhr4mp.eu-west-2.elasticbeanstalk.com:8080/business-unit/";

class BusinessUnitServices{

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

    static getBusinessUnits(){
        return this.fetchDataGET(GET_ALL_BUSINESS_UNITS_URL);
    };
}

export default BusinessUnitServices;