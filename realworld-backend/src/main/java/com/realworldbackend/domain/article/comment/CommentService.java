package com.realworldbackend.domain.article.comment;

import com.realworldbackend.domain.article.Article;
import com.realworldbackend.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public Comment createComment(User commentAuthor, Article article, String body) {
        Comment savedComment = commentRepository.save(Comment.createComment(commentAuthor, article, body));
        article.addComment(savedComment);
        return savedComment;
    }
}
