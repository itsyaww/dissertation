const UPLOAD_REGULATION_SERVICE_URL = '/files/upload/';

async function fetchData(requestBody) {
    const postRequest = {
        method: "POST",
        mode: "cors",
        headers: {'Content-Type': 'application/json'/*, 'Accept': 'multipart/form-data'*/}
    };
    try {
        let response = await fetch(UPLOAD_REGULATION_SERVICE_URL, {
            method: "POST", mode:"cors", headers:{/*"Content-Type" : "multipart/form-data", */"Accept" : "application/json"}, body: requestBody});
        return await response.json()
    }catch(e){
        console.log(e)
    }

}

export function fileUpload(files){
     let formData = new FormData();

     for (const file of files){
         formData.append("files[]",file);
     }

    return fetchData(formData);

}

