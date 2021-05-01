package ch.hearc.qdljee.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import ch.hearc.qdljee.service.CommentService;

@RequestMapping("/comments")
public class CommentsController {
	@Autowired
	CommentService commentService;
}
