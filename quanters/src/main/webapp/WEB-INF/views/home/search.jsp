<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/home/home.js"></script>
<div class="d-flex justify-content-center">
    <div class="searchBar d-flex justify-content-end align-items-md-center">
        <input type="text" id="searchText" autocomplete="off">
        <button id="searchBtn"><img src="${pageContext.request.contextPath}/static/image/search.png" class="searchImage"></button>
    </div>
</div>
<div class="d-flex justify-content-center">
    <div class="autoComplete d-flex justify-content-end align-items-md-center">
        <ul class="searchUl">
        </ul>
    </div>
</div>