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
	$("#logout-link").on('click', function(e) {
		e.preventDefault();
		$("#logout-form").submit();
	});
	// $("#searchBtn").on('click', function(e) {
	// let searchInput = $("#searchInput").val();
	// });
	$("#to-mainmenu").on('click', function(e) {
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