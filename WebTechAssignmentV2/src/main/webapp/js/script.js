var rootUrlGame = 'http://localhost:8080/WebTechAssignmentV2/rest/games';

var rootUrlUser = 'http://localhost:8080/WebTechAssignmentV2/rest/users';

//Count functions 

var countAllGames = function() {
	$.ajax({
		type : 'GET',
		url : rootUrlGame + '/countGames',
		dataType : 'json',
		success : function(data) {
			$('#GamesCard').text(data);
		}
	});
};

var countAllUsers = function() {
	$.ajax({
		type : 'GET',
		url : rootUrlUser + '/countUsers',
		dataType : 'json',
		success : function(data) {
			$('#UsersCard').text(data);
		}
	});
};

// Games' Section

var getAllGames = function() {
	$.ajax({
		type : 'GET',
		url : rootUrlGame,
		dataType : 'json',
		success : renderGamesTable
	});
};

var findGameById = function(id) {
	$.ajax({
		type : 'GET',
		url : rootUrlGame + '/' + id,
		dataType : 'json',
		success : function(data) {
			$('#gameModalHeader').text(data.name);
			$('#boxArt').attr('src', 'images/' + data.boxArt);
			$('#boxArt').show();
			$('#gameId').val(data.id);
			$('#gameId').show();
			$('#idGameLabel').show();
			$('#gameName').val(data.name);
			$('#releaseDate').val(data.releaseDate);
			$('#developer').val(data.developer);
			$('#publisher').val(data.publisher);
			$('#engine').val(data.engine);
			$('#genre').val(data.genre);
			$('#platform').val(data.platform);
			$('#gameImage').val(data.boxArt);
			$('#gameModal').modal('show');
		}
	});
};

var resetGameFields = function() {
	$('#gameModalHeader').hide();
	$('#boxArt').hide();
	$('#gameId').hide();
	$('#idLabel').hide();
	$('#gameName').val('');
	$('#releaseDate').val('');
	$('#developer').val('');
	$('#publisher').val('');
	$('#engine').val('');
	$('#genre').val('');
	$('#platform').val('');
	$('#gameImage').val('');
}

var addGame = function() {
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : rootUrlGame,
		dataType : 'json',
		data : saveGameFormToJSON(),
		success : function(){
			getAllGames();
			countAllGames();
		}
	});
};

var deleteGame = function(id) {
	$.ajax({
		type : 'DELETE',
		url : rootUrlGame + '/' + $('#gameId').val(),
		success : function(){
			getAllGames();
			countAllGames();
		}
	});
};

var updateGame = function() {
	$.ajax({
		type : 'PUT', 
		contentType : 'application/json',
		url : rootUrlGame + '/' + $('#gameModal #gameId').val(),
		dataType : 'json',
		data : updateGameFormToJSON(),
		success : function() {
			getAllGames();
		}
	})
}

var renderGamesTable = function(data) {
	var gamesTable = $('#GamesTable').DataTable();
	gamesTable.destroy();
	$('#GameTableBody').empty();
	$.each(data, function(index, game) {
		$('#GameTableBody').append(
				'<tr>' + '<td>' + game.id + '</td>' + '<td>' + game.name
						+ '</td>' + '<td>' + game.releaseDate + '</td>'
						+ '<td><button type="button" id="' + game.id
						+ '" class="btn btn-info btn-sm">Info</button></td>'
						+ '</tr>');
	});
	$('#GamesTable').DataTable();
};

var updateGameFormToJSON = function() {
	return JSON.stringify({
		"id" : $('#gameId').val(),
		"name" : $('#gameName').val(),
		"releaseDate" : $('#releaseDate').val(),
		"developer" : $('#developer').val(),
		"publisher" : $('#publisher').val(),
		"engine" : $('#engine').val(),
		"genre" : $('#genre').val(),
		"platform" : $('#platform').val(),
		"boxArt" : $('#image').val()
	});
};

var saveGameFormToJSON = function() {
	return JSON.stringify({
		"name" : $('#gameName').val(),
		"releaseDate" : $('#releaseDate').val(),
		"developer" : $('#developer').val(),
		"publisher" : $('#publisher').val(),
		"engine" : $('#engine').val(),
		"genre" : $('#genre').val(),
		"platform" : $('#platform').val(),
		"boxArt" : $('#image').val()
	});
};

// Users' Section

var getAllUsers = function() {
	$.ajax({
		type : 'GET',
		url : rootUrlUser,
		dataType : 'json',
		success : renderUsersTable
	});
};

var findUserByUsername = function(username) {
	$.ajax({
		type : 'GET',
		url : rootUrlUser + '/' + username,
		dataType : 'json',
		success : function(data) {
			$('#modalImage').attr('src', 'images/' + data.image);
			$('#modalImage').show();
			$('#userName').val(data.name);
			$('#username').val(data.username);
			$('#userImage').val(data.image);
			$('#userPassword').hide();
			$('#labelPassword').hide();
			$('#userModal').modal('show');
		}
	});
};

var addUser = function() {
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : rootUrlUser,
		dataType : 'json',
		data : saveUserFormToJSON(),
		success : function(){
			getAllUsers();
			countAllUsers();
		}
	});
};

var updateUser = function() {
	$.ajax({
		type : 'PUT', 
		contentType : 'application/json',
		url : rootUrlUser + '/' + $('#userModal #userId').val(),
		dataType : 'json',
		data : updateUserFormToJSON(),
		success : function() {
			$('#labelPassword').removeAttr('hidden');
			$('#userPassword').removeAttr('hidden');
			$('#username').attr('disabled');
			$('#userPassword').val('');
			getAllUsers();
		}
	})
}

var deleteUser = function(id) {
	$.ajax({
		type : 'DELETE',
		url : rootUrlUser + '/' + $('#username').val(),
		success : function(){
			getAllUsers();
			countAllUsers();
		}
	});
};

var resetUserFields = function() {
	$('#userModalHeader').hide();
	$('#modalImage').hide();
	$('#userId').hide();
	$('#idUserLabel').hide();
	$('#userName').val('');
	$('#username').val('');
	$('#userImage').val('generic.png');
	$('#labelPassword').removeAttr('hidden');
	$('#labelPassword').show();
	$('#userPassword').removeAttr('hidden');
	$('#userPassword').show();
	$('#userPassword').val('');
}

var renderUsersTable = function(data) {
	var usersTable = $('#UsersTable').DataTable();
	usersTable.destroy();
	$('#UserTableBody').empty();
	$.each(data, function(index, user) {
		$('#UserTableBody').append(
				'<tr>' + 
				'<td>' + user.name + '</td>' + 
				'<td>' + user.username + '</td>' + 
				'<td><img src="images/' + user.image 
					+ '" width="30" heigth="30"></td>' + 
				'<td><button type="button" id="' + user.username 
					+ '" class="btn btn-info btn-sm">Info</button></td>' + 
				'</tr>');
	});
	$('#UsersTable').DataTable();
};

var updateUserFormToJSON = function() {
	return JSON.stringify({
		"name" : $('#userName').val(),
		"username" : $('#username').val(),
		"password" : $('#userPassword').val(),
		"image" : $('#userImage').val(),
	});
};

var saveUserFormToJSON = function() {
	return JSON.stringify({
		"name" : $('#userName').val(),
		"username" : $('#username').val(),
		"password" : $('#userPassword').val(),
		"image" : $('#userImage').val(),
	});
};

// Login Section 

var checkUser = function() {
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : rootUrlUser + "/" + $("#inputUserUsername").val() + '/' + $("#inputUserPassword").val(),
		dataType : "json",
		success : function(data, textStatus, jqXHR) {
			$('#sidebarImage').attr('src', 'images/' + data.image);
			$('#sidebarImage').show();
			$('#usernameLabel').text(data.name);
			$('#usernameLabel').show();
			$('#dashboard').removeAttr('hidden');
			$('#dashboard').show();
			$('#LogIn').hide();
			$('#cards').removeAttr('hidden');
			$('#cards').show();
			$('#tables').removeAttr('hidden');
			$('#tables').show();
			countAllGames();
			countAllUsers();
			getAllGames();
			getAllUsers();
		},
		error : function(jqXHR, textStatus, errorThrown) {
			$("#invalidLoginModal").modal();
		}
	})
};

// on document start-up

$(document).ready(function() {
	
	// Login 
	
	$(document).on("click", "#loginButton", function() {
		if ($("#inputUserPassword").val().length == 0
				|| $("#inputUserUsername").val().includes("#")) {
			$("#invalidLoginModal").modal();
		} else {
			checkUser();
		}
	});
	
	// Log out
	$('#sign-out').on('click', '#logout', function() {
		$('#LogIn').show();
		$('#cards').hide();
		$('#tables').hide();
		$('#inputUserUsername').val('');
		$('#inputUserPassword').val('');
		$('#sidebarImage').hide();
		$('#usernameLabel').hide();
		$('#dashboard').hide();
	})
	
	// Game clicks
	
	$('#GameTableBody').on('click', '.btn-info', function() {
		findGameById(this.id);
	});
	$('#gameModal').on('click', '#resetGameFields', function() {
		resetGameFields();
	});
	$('#gameModal').on('click', '#saveGame', function() {
		addGame();
	});
	$('#gameModal').on('click', '#deleteGame', function() {
		deleteGame();
	});
	$('#gameModal').on('click', '#updateGame', function() {
		updateGame();
	})
	
	// User clicks
	
	$('#UserTableBody').on('click', '.btn-info', function(element) { 
		findUserByUsername(this.id);
	});
	$('#userModal').on('click', '#resetUserFields', function() {
		resetUserFields();
	});
	$('#userModal').on('click', '#saveUser', function() {
		addUser();
	});
	$('#userModal').on('click', '#deleteUser', function() {
		deleteUser();
	});
	$('#userModal').on('click', '#updateUser', function() {
		updateUser();
	})

});
