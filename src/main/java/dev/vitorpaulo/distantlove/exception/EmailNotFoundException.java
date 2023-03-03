package dev.vitorpaulo.distantlove.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class EmailNotFoundException extends UsernameNotFoundException {

    public EmailNotFoundException() {
        super("Este usuário não está cadastrado.");
    }
}
