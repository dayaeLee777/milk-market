// userService.js
import { createInstance } from "./index.js";
import store from "../store/index.js";
const instance = createInstance();
const token = store.getters.getJWTToken;
function findUser(success, fail) {
  instance
    .get(`api/users/`, { headers: { Authorization: `Bearer ${token}` } })
    .then(success)
    .catch(fail);
}

function signup(
  email,
  nickName,
  password,
  bname,
  bcode,
  sigungu,
  success,
  fail
) {
  const user = {
    email: email,
    nickname: nickName,
    password: password,
    bname: bname,
    bcode: bcode,
    sigungu: sigungu,
  };

  instance
    .post("api/users/signup", JSON.stringify(user))
    .then(success)
    .catch(fail);
}

function login(email, password, success, fail) {
  const body = {
    email: email,
    password: password,
  };

  instance
    .post("api/users/login", JSON.stringify(body))
    .then(success)
    .catch(fail);
}

function update(user, success, fail) {
  instance.put("api/users", JSON.stringify(user)).then(success).catch(fail);
}

function sendAccessToken(token, success, fail) {
  const accessToken = token;

  instance
    .get(`/api/oauth/kakao?accessToken=${accessToken}`)
    .then(success)
    .catch(fail);
}

export { findUser, signup, login, update, sendAccessToken };