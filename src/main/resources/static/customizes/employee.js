$(function() {
	$(".list-group-item").on('click', function() {
		if ($(this).find("ul")) {
			$(this).toggleClass("tree-closed");
			if ($(this).hasClass("tree-closed")) {
				$("ul", this).hide("fast");
			} else {
				$("ul", this).show("fast");
			}
		}
	});
	$("#logoutLink").on('click', function(e) {
		e.preventDefault();
		$("#logoutForm").submit();
	});
	$("#toMainmenu").on('click', function(e) {
		e.preventDefault();
		let username = $("#userinfo").text();
		window.location.replace('/pgcrowd/employee/to/mainmenu?username=' + username);
	});
	$("#toAdmin").on('click', function(e) {
		e.preventDefault();
		let username = $("#userinfo").text();
		window.location.replace('/pgcrowd/employee/to/pages?username=' + username);
	});
});