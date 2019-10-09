package br.com.inforio.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.inforio.modelo.Autorizacao;
import br.com.inforio.modelo.Perfil;
import br.com.inforio.modelo.Usuario;
import br.com.inforio.repository.PerfilRepository;
import br.com.inforio.repository.UsuarioRepository;

@Service
public class AppUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PerfilRepository perfilRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Usuario> opitionalUsuario = usuarioRepository.findByEmail(email);
        Usuario usuario = opitionalUsuario.orElseThrow(() -> new UsernameNotFoundException("Usuário e/ou senha incorretos."));
        
        return new User(usuario.getEmail(), usuario.getSenha(),  getPermissoes(usuario));
	}

	private Collection<? extends GrantedAuthority> getPermissoes(Usuario usuario) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        Perfil perfil = perfilRepository.findOne(usuario.getPerfil().getCodigo());
        if (perfil != null) {
            Optional<List<String>> autorizacoes = Optional.ofNullable(perfil.getAutorizacoes()
                                                                      .stream()
                                                                      .map(Autorizacao::getNome)
                                                                      .collect(Collectors.toList()));

            autorizacoes.ifPresent(listaPermissoes -> listaPermissoes.forEach(p -> authorities.add(new SimpleGrantedAuthority(p))));
        }

        return authorities;		
	}

}
