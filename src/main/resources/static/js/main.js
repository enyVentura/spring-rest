/*let navbarInfo = "admin/userAuth";
let usersTable = "admin/allUsers";
let addNewUser = "admin/add";
let editUser = "admin/edit";
let deleteUser = "admin/delete/";


let elementCreateUserRoles = document.getElementById("newRoles");
let elementEditUserRoles = document.getElementById("editRole");

$(document).ready(function () {
    getAuthUser();
    getUsersTable();
})

function authUserForm(title, roles) {
    document.getElementById("userTitle").textContent = title;
    document.getElementById("roleTitle").textContent = roles;
}

async function getAuthUser() {
    fetch(navbarInfo)
        .then(response => (response.json()))
        .then(data => {
            authUserForm(data.email, JSON.stringify(data.setRoles
                .map(role => role.name.substring(5)).join(", ")));
        })
}

//Pill Table
$("#v-pills-home-tab").click(function () {
    getUsersTable();
});
//Pill Info
$("#v-pills-profile-tab").click(function () {
    $("#userInformation").empty();
    $.ajax({
        type: 'GET',
        url: navbarInfo,
        dataType: "json",
        success: function (data) {
            $('#userInformation').append($('<tr>').append(
                $('<td>').text(data.id),
                $('<td>').text(data.username),
                $('<td>').text(data.firstName),
                $('<td>').text(data.lastName),
                $('<td>').text("**********"),
                $('<td>').text(JSON.stringify(data.setRoles.map(role => role.name.substring(5)).join(", ")))
            ))
        }
    });
});

//Table of all Users
function getUsersTable() {
    $("#tbody").empty();
    $.ajax({
        type: 'GET',
        url: usersTable,
        dataType: "json",
        success: function (data) {
            $.each(data, function (i, user) {
                $('#tbody').append($('<tr>').append(
                    $('<td>').text(user.id),
                    $('<td>').text(user.username),
                    $('<td>').text(user.firstName),
                    $('<td>').text(user.lastName),
                    $('<td>').text("**********"),
                    $('<td>').text(user.setRoles.map(role => role.name.substring(5)).join(", ")),
                    $('<td>').append($('<button>').text("Edit").attr({
                        "type": "button",
                        "class": "btn btn-primary edit",
                        "data-toggle": "modal",
                        "data-target": "#editModal",
                    }).data("user", user)),
                    $('<td>').append($('<button>').text("DELETE").attr({
                        "type": "button",
                        "class": "btn btn-danger delete",
                        "data-toggle": "modal",
                        "data-target": "#deleteModal",
                    }).data("user", user))
                ))
            });
        }
    });
}

//Create new User
$(document).on("click", ".addNew", function () {
    $("#home-tab").trigger('click');
    let roleSelectedValues = Array.from(elementCreateUserRoles.selectedOptions).map(el => el.value);
    let roleArray = convertToRoleSet(roleSelectedValues);

    let newUserForm = {

        username: $("#newNickname").val(),
        firstName: $("#newFirstName").val(),
        lastName: $("#newLastName").val(),
        password: $("#newPassword").val(),
        setRoles: roleArray
    };
    $.ajax({
        type: 'POST',
        url: addNewUser,
        dataType: 'json',
        contentType: 'application/JSON; charset=utf-8',
        data: JSON.stringify(newUserForm),
        success: function () {
            $('form[id=newUserForm]').trigger('reset');
            getUsersTable();
        }
    });
});

//Edit User Modal Window
//Fill
$(document).on("click", ".edit", function () {
    let user = $(this).data('user');

    $("#editId").val(user.id);
    $("#editNickname").val(user.username);
    $("#editFirstName").val(user.firstName);
    $("#editLastName").val(user.lastName);
    $("#editPassword").val("**********");
    $("#editRole").val(user.setRoles.map(role => role.name));
});
//Edit
$(document).on("click", ".editUser", function () {

    let roleSelectedValues = Array.from(elementEditUserRoles.selectedOptions).map(el => el.value);
    let roleArray = convertToRoleSet(roleSelectedValues);

    let editUserForm = {

        id: $("#editId").val(),
        username: $("#editNickname").val(),
        firstName: $("#editFirstName").val(),
        lastName: $("#editLastName").val(),
        password: $("#editPassword").val(),
        setRoles: roleArray
    };

    $.ajax({
        type: 'PUT',
        url: editUser,
        dataType: 'json',
        contentType: 'application/JSON; charset=utf-8',
        data: JSON.stringify(editUserForm),
        success: getUsersTable()
    });
});
//Delete
$(document).on("click", ".delete", function () {
    let delUser = $(this).data('user');

    $("#delId").val(delUser.id);
    $("#delNickname").val(delUser.username);
    $("#delFirstName").val(delUser.firstName);
    $("#delLastName").val(delUser.lastName);
    $("#delPassword").val("**********");
    $("#delRole").val(delUser.setRoles.map(role => role.name.substring(5)).join(", "));
});
//DeleteButton
$(document).on("click", ".deleteButton", function () {
    let id = $("#delId").val();
    $.ajax({
        method: 'DELETE',
        url: deleteUser + id,
        contentType: "application/json",
        dataType: "json",
        success: getUsersTable()
    });
});

//Role Set Converter
function convertToRoleSet(Array) {
    let roleArray = [];

    if (Array.indexOf("ROLE_USER") !== -1) {
        roleArray.unshift({id: 1, name: "ROLE_USER"});
    }
    if (Array.indexOf("ROLE_ADMIN") !== -1) {
        roleArray.unshift({id: 2, name: "ROLE_ADMIN"});
    }
    return roleArray;
}*/


$('document').ready(function () {
    $.ajax('/users/userAuth', {
        method: 'GET',
        success: function (user) {
            $('#nameTitle').text(user.username);
            user.roles.forEach(function (role) {
                $('#roleTitle').append('<strong>' + role.simpleName + ' </strong>');
            })
        }
    })
    showUsers();
})
//строит таблицу всех юзеров
function showUsers() {
    $('#users').empty();
    $.ajax("/users", {
        dataType: "json",
        method: 'GET',
        success: function (data) {
            let users = JSON.parse(JSON.stringify(data));
            users.forEach(function (user) {
                $("#users").append('<tr style="height: 63px;" id="tr' + user.id + '">' +
                    '<td style="width: 77px;height: 63px;" id="userId' + user.id + '">' + user.id + '</td>' +
                    '<td style="height: 63px;width: 200px;" id="userName' + user.id + '">' + user.username + '</td>' +
                    '<td style="height: 63px;width: 200px;" id="userRoles' + user.id + '"></td>' +
                    '<td style="height: 63px;width: 163px;">' +
                    '<button class="btn btn-info" type="button" data-toggle="modal" data-target="#edit" onclick="openEditeModal(' + user.id + ')">Edit</button>' +
                    '</td>' +
                    '<td style="height: 63px;width: 143px;">' +
                    '<button class="btn btn-danger" type="button" data-toggle="modal" data-target="#delete" onclick="openDeleteModal(' + user.id + ')">Delete</button>' +
                    '</td>' +
                    '</tr>');
                user.roles.forEach(function (role) {
                    $("#userRoles" + user.id).append('<span>' + role.simpleName + ' </span>');
                })
            })
        }
    })
}

//открывает модалку для удаления
function openDeleteModal(id) {
    let name = $('#userName' + id).text();
    let roles = $('#userRoles' + id).text().trim().split(" ");
    $('#delete #id').val(id);
    $('#delete #name').val(name);
    $('#delete #roles').empty();
    $.each(roles, function (key, value) {
        $('#delete #roles').append('<option value="' + key + '">' + value + '</option>');
    });
}

//кнопка в модалке удаляет юзверя
$('#deleteUser').on('click', function deleteUser() {
    let id = $('#delete #id').val();
    $.ajax('/users/' + id, {
        method: 'DELETE',
        success: function () {
            $("#users").find('#tr' + id).remove();
        }
    })
})

//открывает модалку редактирования
function openEditeModal(id) {
    let name = $('#userName' + id).text();
    $('#idEdite').val(id);
    $('#nameEdite').val(name);
}

//кнопка в модалке редактирования
$('.btn-primary').on('click', function (event) {
    event.preventDefault();
    let user = {
        id: $('#idEdite').val(),
        name: $('#nameEdite').val(),
        password: $('#passEdite').val(),
        roles: $('#rolesEdite').val()
    };
    $.ajax('/users/edit', {
        data: JSON.stringify(user),
        dataType: 'json',
        contentType: 'application/JSON; charset=utf-8',
        method: 'PUT',
        success: function () {
            showUsers();
        }
    })
})

//добавление юзера
$('.btn-success').on('click', function (event) {
    event.preventDefault();
    let user = {
        name: $('#addName').val(),
        password: $('#addPass').val(),
        roles: $('#addRole').val()
    };
    $.ajax('/users/add', {
        data: JSON.stringify(user),
        dataType: 'json',
        contentType: 'application/JSON; charset=utf-8',
        method: 'POST',
        success: function () {
            $('#tab1').addClass('active');
            $('#tab-1').addClass('active');
            $('#tab2').removeClass('active');
            $('#tab-2').removeClass('active');
            $('#addName').val('User name');
            $('#addPass').val('Password');
            showUsers();
        }
    })
})
