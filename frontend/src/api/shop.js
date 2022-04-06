import store from "../store/index.js";
import { createInstance } from "./index.js";
const instance = createInstance();

// const bcode = store.state.user.bcode;

function getItemList (sortBy, order, page, size, success, fail) {
  const ItemList = {
    bcode: store.state.user.bcode,
    sortBy: sortBy,
    order: order,
    page: page,
    size: size,
  };
  instance
    .post("/api/item/search", JSON.stringify(ItemList), {
      headers: {
        Authorization: `Bearer ${store.state.user.JWTToken
          }`
      },
    })
    .then(success)
    .catch(fail);
}

function getSearchItem (
  keyword,
  bcode,
  sortBy,
  order,
  page,
  size,
  success,
  fail
) {
  const ItemList = {
    fields: ["description", "itemName"],
    searchTerm: keyword,
    bcode: bcode,
    sortBy: sortBy,
    order: order,
    page: page,
    size: size,
  };

  instance
    .post("/api/item/search", JSON.stringify(ItemList), {
      headers: {
        Authorization: `Bearer ${store.state.user.JWTToken
          }`
      },
    })
    .then(success)
    .catch(fail);
}

function findItemListByPage (pageNumber, success, fail) {
  const ItemList = {
    bcode: store.state.user.bcode,
    sortBy: sortBy,
    order: order,
    page: pageNumber,
    size: size,
  };
  instance
    .get("/api/item/search/" + pageNumber)
    .then(success)
    .catch(fail);
}

export { findItemListByPage, getItemList, getSearchItem };
