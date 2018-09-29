package article.service;
//게시글 보기에 필요한 객체.

import article.model.Article;
import article.model.ArticleContent;

public class ArticleData {
	private Article article;
	private ArticleContent content;
	
	public ArticleData(Article article, ArticleContent content) {
		this.article = article;
		this.content = content;
	}

	public Article getArticle() {
		return article;
	}

	public String getContent() {
		return content.getContent();
	}
	
	
	
}
