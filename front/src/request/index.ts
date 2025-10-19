import 'axios'
import axios from 'axios'

const myAxios = axios.create({
    baseURL: "/api",
    timeout: 6000,
    withCredentials: true
})

export default myAxios