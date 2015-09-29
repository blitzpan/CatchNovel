<html>
<head>
	<title>${title}</title>
	<meta http-equiv="content-type" content="text/html;charset=gbk">
<style>
h1{
	background:#4178B0;
	color:#FFFFFF;
}
p{
	text-indent:2em;
}
a{
	text-decoration:none;
}
.container{
	padding:0 20px;
	background:#EEEEEE;
}
.authorInfo{
	color:#888888;
}
.author {
	float:left;
}
.date{
	float:right;
}
.clr{
	clear:both;
}
#content a{
	color:#888888;
	font-size:xx-small;
}
</style>
</head>
<body>
	<div class="container">
		<center><h1><a href='${homeUrl}' target='_blank'>${title}</a></h1></center>
		<div class="authorInfo">
			<div class="author">${authorName}</div>
			<div class="date">${sendTime}</div>
			<div class="clr"></div>
		</div>
		<div id='content'>
			<p>${content}</p>
			<a href="${url}" target="_blank">查看原文</a>
		</div>
	</div>
</body>
</html>
