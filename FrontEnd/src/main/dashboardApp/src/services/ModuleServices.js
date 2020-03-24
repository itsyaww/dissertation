const GET_ALL_MODULES_URL="/module/";

class ModuleServices{

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

    static getModules(){
        return this.fetchDataGET(GET_ALL_MODULES_URL);
    };
}

export default ModuleServices;