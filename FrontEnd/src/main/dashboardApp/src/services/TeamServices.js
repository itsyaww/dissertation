const GET_ALL_TEAMS_URL="http://backend-env.eba-tykhr4mp.eu-west-2.elasticbeanstalk.com:8080/team/";

class TeamServices{

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

    static getTeams(){
        return this.fetchDataGET(GET_ALL_TEAMS_URL);
    };
}

export default TeamServices;