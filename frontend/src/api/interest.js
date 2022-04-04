import store from "../store/index.js";
import { createInstance } from "./index.js";

const token = store.state.user.JWTToken;
const instance = createInstance();

function getHotItem(success, fail) {
  instance.get("/api/interest/hotitem/").then(success).catch(fail);
}

export { getHotItem };
