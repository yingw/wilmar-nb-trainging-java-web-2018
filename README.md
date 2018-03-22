# Wilmar NB Training Java Web
2018-03 by Yin Guo Wei

All codes can be download from: http://git.wilmar.cn/YinGuowei/wilmar-nb-trainging-java-web-2018


## Demo 1: Web

First of all, let's build this static web application to learn some HTML knowledge.

### Basic Html

create a new index.html:
```html
<html>
<body>
Hello, Wilmar!
</body>
</html>
```

### Form

insert form's code in body:
```html
<form action="#" method="post">
username:<input type="text" name="username"><br>
password:<input type="password" name="password"><br>
<input type="submit"></form>

```

### Table

create a table:
```html
<table border="1">
<thead>
<tr>
<th>Username</th>
<th>Password</th>
</tr>
</thead>
<tbody>
<tr>
<td>yinguowei</td>
<td>******</td>
</tr>
</tbody>
</table>
```

### To learn more about HTML
http://www.runoob.com/html/html-basic.html

### Simple Style

What's CSS: Cascading Style Sheets

Write one, insert in head:

```html
<style>
    body {
        background-color: #d0e4fe;
    }

    h1 {
        color: orange;
        text-align: center;
    }

    p {
        font-family: "Times New Roman";
        font-size: 20px;
    }
</style>
```

### Bootstrap
What's Bootstrap:
http://www.bootcss.com/

```html
<script src="http://apps.bdimg.com/libs/jquery/2.1.1/jquery.min.js"></script>
<link rel="stylesheet" href="https://apps.bdimg.com/libs/bootstrap/3.2.0/css/bootstrap.min.css">
```

```html
<table class="table table-striped table-bordered">
<input class="btn btn-primary" ...>
```

### Grid System

```html
<row>
    <div class="col-sm-3"></div>
</row>
```

### To learn more about CSS

http://www.runoob.com/css/css-tutorial.html
http://www.runoob.com/bootstrap/bootstrap-intro.html

### Javascript Alert
```html
<input value="Clieck Me!" type="button" onclick="javascript:alert('Hello, Wilmar!');">
```

### js Function
```html
<input value="Clieck Me!" type="button" onclick="javascript:alertWilmar('yinguowei');">

<script>
    function alertWilmar(name) {
        alert('Hello, ' + name + '!');
    }
</script>
```

### Dom
What's Dom: Document Object Model

```html
<script>
    function alertWilmar() {
        alert('Hello, ' + document.getElementById('username').value + '!');
    }
</script>
```

```html
<input value="Clieck for date!" type="button" onclick="javascript:alert(Date());">
```
### To learn more about Javascript
http://www.runoob.com/js/js-tutorial.html


### Use template
https://startbootstrap.com/

Download one, copy to project folder, DONE!

## Demo 2: Java Application

### Simple Java
Write a HelloWilmar.java
```java
public class HelloWilmar {
	public static void main(String[] args) {
		System.out.println("Hello, Wilmar!");
	}
}
```
Compile and run:
```sh
javac HelloWilmar.java
java HelloWilmar
```

Demo2

```java
public class HelloWilmar {
	public static void main(String[] args) {
		System.out.println("Hello, " + args[0] + "!");
	}
}
```

```sh
java HelloWilmar yinguowei
```

### Spring Boot Application

Create a new application from http://start.spring.io, choose dependencies: JPA, Web, Thymeleaf, Rest Respsitory, H2, Lombok, DevTools, choose spring boot version: 2.x.x.RELEASE

some properties, in application.properties:
```properties
spring.data.rest.base-path=/api
spring.thymeleaf.cache=false
spring.jpa.show-sql=true
spring.jpa.open-in-view=false
```

Create a home.html in templates folder

First Hello Controller:
```java
@RestController
class HelloController {
	@RequestMapping("/")
	public String hello() {
		return "Hello, Wilmar!";
	}
}

@Controller
class HomeController {
	@RequestMapping("/home")
	public String home() {
		return "home";
	}
}
```

Create more complex (include H2 database functions) application:

## Create User entity

```java
@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
class User {
    @Id
    @GeneratedValue
    Long id;

    @NonNull
    String username;

    @NonNull
    String fullname;

    @NonNull
    String email;

}

```

## User Repo

```java
@RepositoryRestResource
interface UserRepository extends JpaRepository<User, Long> {
}
```

### Use CURL to test

http://blog.kent-chiu.com/2013/08/14/testing-rest-with-curl-command.html

```sh
## list
$ curl http://localhost:8080/api/users
## create
curl http://localhost:8080/api/users -X POST -H "Content-Type:application/json" -H "Accept:application/json" -d '{"username":"yinguowei", "fullname":"Yin Guo Wei", "email":"yinguwoei@gmail.com"}'
## delete
curl http://localhost:8080/api/users/2 -X DELETE
## update
curl http://localhost:8080/api/users/1 -X PUT -H "Content-Type:application/json" -H "Accept:application/json" -d '{"username":"jon", "fullname":"Jon", "email":"jon@cn.wilmar-intl.com"}'
```

### H2 console
http://localhost:8080/h2-console/
jdbc:h2:mem:testdb/sa/

## Demo 3: Admin Frontend

Visit https://startbootstrap.com/template-overviews/sb-admin-2/ and download

copy js/data/dist/vendar to static, copy index.html and pages to templates

## Dynamic controller 

```java
@GetMapping("/")
public String index() {
    return "index";
}

@GetMapping("/pages/{path}.html")
public String page(@PathVariable("path") String path) {
    return "pages/" + path;
}
```

### User Controller

```java
@GetMapping("/users")
public ModelAndView users() {
    return new ModelAndView("user/userList", "users", userRepository.findAll());
}

@GetMapping("/users/new")
public ModelAndView newUser() {
    return new ModelAndView("user/userForm", "user", new User());
}

@PostMapping("/users")
public String save(User user) {
    userRepository.save(user);
    return "redirect:/users";
}
```

### UserList

Copy table.html to templates/user/userList.html, and replace table as:

Set head:
```html
<html lang="en" xmlns:th="http://www.thymeleaf.org">
```

```html
<table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-example">
    <thead>
        <tr>
            <th>Username</th>
            <th>Fullname</th>
            <th>Email</th>
            <th></th>
        </tr>
    </thead>
    <tbody>
    <!--/*@thymesVar id="user" type="com.example.User"*/-->
        <tr class="odd gradeX" th:each="user : ${users}">
            <td th:text="${user.username}">Trident</td>
            <td th:text="${user.fullname}">Internet Explorer 4.0</td>
            <td th:text="${user.email}">Win 95+</td>
            <td class="center"><input type="button" class="btn btn-info" value="View"></input>
                <input type="button" class="btn btn-warning" value="Edit"></input>
                <input type="button" class="btn btn-danger" value="Delete"></input></td>
        </tr>
    </tbody>
</table>
```

Copy another userForm.html

### UserForm

```html
<form role="form" th:object="${user}" method="post" action="/users">
    <div class="form-group">
        <label>Username</label>
        <input class="form-control" name="username" th:value="*{username}">
    </div>
    <div class="form-group">
        <label>Fullname</label>
        <input class="form-control" name="fullname" th:value="*{fullname}">
    </div>
    <div class="form-group">
        <label>Email</label>
        <input class="form-control" name="email" th:value="*{email}">
    </div>
    <button type="submit" class="btn btn-primary">Submit</button>
    <button type="reset" class="btn btn-default">Reset</button>
</form>

```

visit http://localost:8080/users and http://localost:8080/users/new , 

Test, good luck!