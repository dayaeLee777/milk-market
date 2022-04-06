import store from "@/store";
import { createInstance } from "./index.js";


const instance = createInstance();

function findAll (success, fail) {
  instance.get("/api/item/").then(success).catch(fail);
}
function getItemList (bcode, sortBy, order, page, size, success, fail) {
  const ItemList = {
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
//판매, 대여 여부로 검색
function getSearchItemByDivision (
  division,
  category,
  bcode,
  sortBy,
  order,
  page,
  size,
  success,
  fail
) {
  const ItemList = {
    division: division,
    category: category,
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
//카테고리로 검색
function getSearchItemByCategory (
  category,
  division,
  bcode,
  sortBy,
  order,
  page,
  size,
  success,
  fail
) {
  const ItemList = {
    category: category,
    division: division,
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

//우리동네 찾기
function getSearchItemByBcode (
  category,
  division,
  sortBy,
  order,
  page,
  size,
  success,
  fail
) {
  console.log("bcode in getSearchItemByBcode" + bcode);
  console.log("category in js : ", category);
  console.log("division in js : ", division);
  console.log("");
  const ItemList = {
    bcode: bcode,
    category: category,
    division: division,
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

//전체 목록 찾기
function getTotalPage (success, fail) {
  const ItemList = {
    fields: [],
    searchTerm: null,
    bcode: null,
    sortBy: "regDateTime",
    order: null,
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
    bcode: bcode,
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

function findItemsByOwner (userId, success, fail) {
  instance
    .get("/api/item/seller/" + userId)
    .then(success)
    .catch(fail);
}

function findById(itemId, success, success2, fail) {
  instance
    .get("/api/item/" + itemId, {
      headers: {
        Authorization: `Bearer ${store.state.user.JWTToken
          }`
      },
    })
    .then(success)
    .then(success2)
    .catch(fail);
}

function findHistoryById (itemId, success, fail) {
  instance
    .get("/api/item/history/" + itemId)
    .then(function (response) {
      success(response);
    })
    .catch(fail);
}

function findBySeller (id, success, fail) {
  instance
    .get("/api/item/seller/" + id)
    .then(success)
    .catch(fail);
}
// 구매 완료한 상품들 가져오기
function findByBuyer (id, success, fail) {
  instance
    .get("/api/item/buyer/" + id)
    .then(success)
    .catch(fail);
}
// 입찰 중인 상품들 가져오기
function findByBidder (id, success, fail) {
  instance
    .get("/api/item/bidder/" + id)
    .then(success)
    .catch(fail);
}

function create (body, success, fail, final) {
  instance
    .post("/api/item", JSON.stringify(body))
    .then(success)
    .catch(fail)
    .finally(final);
}

function update (body, success, fail) {
  instance.put("/api/item", JSON.stringify(body)).then(success).catch(fail);
}

function remove (id, success, fail) {
  instance
    .delete("/api/item/" + id)
    .then(success)
    .catch(fail);
}

// 구매자가 배송중인 상품을 구매 확정
function confirm (itemId, buyer, success, fail) {
  instance
    .put("/api/item/" + itemId + "/by/" + buyer)
    .then(success)
    .catch(fail);
}

function findMySaleItems (userId, success, fail) {
  instance
    .get("api/item/of/" + userId)
    .then((res) => success(res))
    .catch((err) => fail(err));
}

export {
  findAll,
  findItemListByPage,
  findItemsByOwner,
  findById,
  findHistoryById,
  findBySeller,
  findByBuyer,
  findByBidder,
  create,
  update,
  remove,
  confirm,
  findMySaleItems,
  getItemList,
  getSearchItem,
  getSearchItemByDivision,
  getSearchItemByCategory,
  getSearchItemByBcode,
  getTotalPage,
};
