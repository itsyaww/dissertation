const GET_ALL_FILES_URL="/files/";

class FileServices{

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

    static getFiles(){
        return this.fetchDataGET(GET_ALL_FILES_URL);
    };
}

export default FileServices;