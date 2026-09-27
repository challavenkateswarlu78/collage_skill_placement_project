public class MysqllPW {
    //spring.datasource.url=jdbc:mysql://localhost:3306/college_skill_portal
    //spring.datasource.username=root
    //spring.datasource.password=venkat@78

    //OR
    //spring.datasource.password=${DB_PASSWORD}

    // powershell  $env:DB_PASSWORD="venkat@78"

    //jwt.secret=collegeSkillPlacementPortalSecretKeyForJwtAuthentication2026
    //or
    //jwt.secret=${JWT_SECRET}
    //PowerShell $env:JWT_SECRET="collegeSkillPlacementPortalSecretKeyForJwtAuthentication2026"

    //.\mvnw spring-boot:run

    /* $env:DB_PASSWORD="venkat@78"
    $env:JWT_SECRET="collegeSkillPlacementPortalSecretKeyForJwtAuthentication2026"
    .\mvnw spring-boot:run
     */

    //http://localhost:8080/swagger-ui/index.html  check open or not in chrome
    //curl.exe http://localhost:8080/v3/api-docs
    //curl.exe -i http://localhost:8080/v3/api-docs
}
