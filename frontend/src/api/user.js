// userService.js
import { createInstance } from "./index.js";

const instance = createInstance();

function findById (id, success, fail) {
  instance
    .get(`api/users/${id}`)
    .then(success)
    .catch(fail);
}

<<<<<<< HEAD
function signup(email, nickName, password, success, fail) {
=======
function signup (email, name, password, success, fail) {
>>>>>>> 1dafa85a821a297f205a58f41b42f7be8b112181
  const user = {
    email: email,
    nickname: nickName,
    password: password
  };

  instance
    .post("api/users/signup", JSON.stringify(user))
    .then(success)
    .catch(fail);
}

function login (email, password, success, fail) {
  const body = {
    email: email,
    password: password
  };

  instance
    .post("api/users/login", JSON.stringify(body))
    .then(success)
    .catch(fail);
}

function update (user, success, fail) {
  instance
    .put("api/users", JSON.stringify(user))
    .then(success)
    .catch(fail);
}

function sendAccessToken (token, success, fail) {
  const accessToken = token

  instance
    .get(`/api/oauth/kakao?accessToken=${accessToken}`)
    .then(success)
    .catch(fail);
}

export { findById, signup, login, update, sendAccessToken };
