package net.xeill.elpuig.apipatitasconectadas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.xeill.elpuig.apipatitasconectadas.models.PostModel;
import net.xeill.elpuig.apipatitasconectadas.models.UserModel;
import net.xeill.elpuig.apipatitasconectadas.models.GrupoModel;
import net.xeill.elpuig.apipatitasconectadas.repos.IPostRepository;
import net.xeill.elpuig.apipatitasconectadas.repos.IUserRepository;
import net.xeill.elpuig.apipatitasconectadas.repos.IGrupoRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private IPostRepository postRepository;
    
    @Autowired
    private IUserRepository userRepository;
    
    @Autowired
    private IGrupoRepository grupoRepository;

    public List<PostModel> getPosts() {
        return postRepository.findAll();
    }

    @Transactional
    public PostModel savePost(PostModel post) {
        validatePost(post);
        post.setFecha(LocalDateTime.now());
        return postRepository.save(post);
    }

    public PostModel getById(Long id) {
        return postRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Post no encontrado con id: " + id));
    }

    @Transactional
    public PostModel updateById(PostModel request, Long id) {
        PostModel post = getById(id);
        
        if (request.getContenido() != null) {
            post.setContenido(request.getContenido());
        }
        if (request.getImg() != null) {
            post.setImg(request.getImg());
        }
        if (request.getCreador() != null) {
            validateUser(request.getCreador().getId());
            post.setCreador(request.getCreador());
        }
        if (request.getGrupo() != null) {
            validateGrupo(request.getGrupo().getId());
            post.setGrupo(request.getGrupo());
        }
        
        return postRepository.save(post);
    }

    @Transactional
    public boolean deletePost(Long id) {
        try {
            postRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public List<PostModel> getPostsByUser(Long userId) {
        UserModel user = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id: " + userId));
        return postRepository.findByCreador(user);
    }
    
    public List<PostModel> getPostsByGrupo(Long grupoId) {
        GrupoModel grupo = grupoRepository.findById(grupoId)
            .orElseThrow(() -> new EntityNotFoundException("Grupo no encontrado con id: " + grupoId));
        return postRepository.findByGrupo(grupo);
    }
    
    public List<PostModel> searchPostsByContent(String searchTerm) {
        return postRepository.searchByContent(searchTerm);
    }
    
    public List<PostModel> getPostsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return postRepository.findByDateRange(startDate, endDate);
    }
    
    private void validatePost(PostModel post) {
        if (post.getContenido() == null || post.getContenido().trim().isEmpty()) {
            throw new ValidationException("El contenido del post no puede estar vacío");
        }
        if (post.getCreador() == null || post.getCreador().getId() == null) {
            throw new ValidationException("El creador del post es obligatorio");
        }
        validateUser(post.getCreador().getId());
        if (post.getGrupo() != null && post.getGrupo().getId() != null) {
            validateGrupo(post.getGrupo().getId());
        }
    }
    
    private void validateUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("Usuario no encontrado con id: " + userId);
        }
    }
    
    private void validateGrupo(Long grupoId) {
        if (!grupoRepository.existsById(grupoId)) {
            throw new EntityNotFoundException("Grupo no encontrado con id: " + grupoId);
        }
    }
}