// class BaseAPI {

/** get data
 */
async function getAll(_url) {
    return (await fetch(_url)).json();
}

/** filter data
 */
async function filterData(_url, params) {
    Object.keys(params).forEach(key => _url.searchParams.append(key, params[key]))
    return (await fetch(_url)).json();
}

/**
 * post data
 */
async function postData(_url, data) {
    const settings = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data)
    };
    return (await fetch(_url, settings)).json();
}

/**
 * put data
 */
async function putData(_url, data) {
    const settings = {
        method: 'PUT',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data)
    };
    return (await fetch(_url, settings)).json();
}

/**
 * delete data
 */
async function deleteData(_url, id) {
    return (await fetch(_url + id, {
        method: 'DELETE',
    })).json();
}
// }

// export default new BaseAPI();