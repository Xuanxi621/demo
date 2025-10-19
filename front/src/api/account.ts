import request from '@/request'

export default function getAccountList() {

    return request('/account/list', {
        method: 'GET'
    })
}
