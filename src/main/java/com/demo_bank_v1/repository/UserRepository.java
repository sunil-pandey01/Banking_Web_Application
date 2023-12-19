package com.demo_bank_v1.repository;

import com.demo_bank_v1.models.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository  extends CrudRepository<User, Integer> {

    @Query(value = "SELECT email FROM user WHERE email = :email", nativeQuery = true)
    String getUserEmail(@Param("email")String email);

    @Query(value = "SELECT password FROM user WHERE email = :email", nativeQuery = true)
    String getUserPassword(@Param("email")String email);

    @Query(value = "SELECT verified FROM user WHERE email = :email", nativeQuery = true)
    int isVerified(@Param("email")String email);

    @Query(value = "SELECT * FROM user WHERE email = :email", nativeQuery = true)
    User getUserDetails(@Param("email")String email);

    @Modifying
    @Query(value = "INSERT INTO user (first_name, last_name, email, password, token, code, verified) VALUES" +
            "(:first_name, :last_name, :email, :password, :token, :code, :verified)", nativeQuery = true )
    @Transactional
    void registerUser(@Param("first_name")String first_name,
                      @Param("last_name") String last_name,
                      @Param("email")String email,
                      @Param("password")String password,
                      @Param("token") String token,
                      @Param("code")int code,
                      @Param("verified") int verified);

    @Modifying
    @Query(value = "UPDATE user SET token=null, code=null, verified_at=NOW(), updated_at=NOW() WHERE " +
            "token= :token AND code= :code", nativeQuery = true)
    @Transactional
    void verifyAccount(@Param("token")String token, @Param("code") String code);

    @Query(value = "SELECT token FROM user WHERE token = :token" , nativeQuery = true)
    String checkToken(@Param("token")String token);

}
