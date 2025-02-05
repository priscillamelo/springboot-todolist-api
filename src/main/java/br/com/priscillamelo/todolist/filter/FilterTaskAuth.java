package br.com.priscillamelo.todolist.filter;

import java.io.IOException;

import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import at.favre.lib.crypto.bcrypt.BCrypt.Result;
import br.com.priscillamelo.todolist.user.IUserRepository;
import br.com.priscillamelo.todolist.user.UserModel;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {
    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String servletPath = request.getServletPath();

        if (servletPath.equals("/tasks/create-task")) {
            String auth = request.getHeader("Authorization");
            String authEncoder = auth.substring("Basic".length()).trim();

            byte[] authDecoder = Base64.getDecoder().decode(authEncoder);
            String userAuth = new String(authDecoder);
            String[] credentials = userAuth.split(":");
            String username = credentials[0];
            String password = credentials[1];

            List<UserModel> listUser = this.userRepository.findByUsername(username);
            UserModel user = listUser.getFirst();

            if (user == null) {
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "Usuário não cadastrado!");
            } else {
                Result result = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());

                if (result.verified) {
                    request.setAttribute("idUser", user.getId());
                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(HttpStatus.UNAUTHORIZED.value(), "Senha inválida!");
                }
            }

        } else {
            filterChain.doFilter(request, response);
        }

    }

}
