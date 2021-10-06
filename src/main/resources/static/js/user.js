let index = {
	init: function() {
		$("#btn-save").on("click", ()=>   { // function(){} 대신 ()=>{} 를 사용하는 이유는 this를 바인딩 하기 위해서!! 그냥 전자처럼 구현하게 되면 this를 바인딩해서 사용해야함(복잡해짐)
			this.save();
		});
		/*		
		$("#btn-login").on("click",()=> {
			this.login();
		});*/
		$("#btn-update").on("click", ()=>   {
			this.update();
		});
	},


	save: function() {
		//alert('user의 save함수 호출됨');
		let data = { //id를 찾아서 값을 가져온다.
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};

		// console.log(data)

		// ajax 호출시 default가 비동기 호출
		//ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
		//ajax가 통신을 성공하고 서버가 json을 리턴해주면 자동으로 자바 오브젝트로 변환 해준다. = dataType: "json" 생략가능!
		$.ajax({
			// 회원가입 수행 요청
			type: "POST",
			url: "/auth/joinProc",
			data: JSON.stringify(data), //JSON 스트링으로 변경해 데이터를 전송한다. / http body 데이터 : 이 경우 MINE 타입이 필요해진다.
			contentType: "application/json; charset=utf-8", //body 데이터가 어떤 타입인지(MINE)
			//dataType: "json" //요청을 서버로 해서 응답이 왔을 때, 기본적으로 모든 것이 문자열 (생긴게 json이라면?)  javaScript 오브젝트로 변경
		}).done(function(resp) {

			alert("회원가입이 완료 되었습니다.");
			console.log(resp);
			location.href = "/";
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	},
	/*login: function(){
		//alert('user의 save함수 호출됨');
		let data = { //id를 찾아서 값을 가져온다.
			username: $("#username").val(),
			password: $("#password").val()
		};
		
		$.ajax({
			// 회원가입 수행 요청
			type:"POST",
			url:"api/user/login",
			data: JSON.stringify(data), //JSON 스트링으로 변경해 데이터를 전송한다. / http body 데이터 : 이 경우 MINE 타입이 필요해진다.
			contentType:"application/json; charset=utf-8", //body 데이터가 어떤 타입인지(MINE)
			//dataType: "json" //요청을 서버로 해서 응답이 왔을 때, 기본적으로 모든 것이 문자열 (생긴게 json이라면?) -> javaScript 오브젝트로 변경
		}).done(function(resp){
			
			alert("로그인이 완료 되었습니다.");
			console.log(resp);
			location.href = "/";
		}).fail(function(error){
			alert(JSON.stringify(error));	
		}); 
	}*/
	update: function() {
		let data = {
			id: $("#id").val(),
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};

		$.ajax({
			type: "PUT",
			url: "/user",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
		}).done(function(resp) {
			alert("회원 정보 수정이 완료 되었습니다.");
			console.log(resp);
			location.href = "/";
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	},
}

index.init();