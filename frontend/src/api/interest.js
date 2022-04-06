import store from "@/store";
import { createInstance } from "./index.js";


const instance = createInstance();

function getHotItem (success, fail) {
  instance.get("/api/interest/hotitem/").then(success).catch(fail);
}

export { getHotItem };
