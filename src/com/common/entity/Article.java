package com.common.entity;

import com.common.PostgreSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Article extends Publication{
    private String journal;
    private static String statement = "INSERT INTO article(pub_id, pub_key, title, pub_date, journal) VALUES "+
            "(?,?,?,?,?)";

    public Article() {
        this.journal = null;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    @Override
    PreparedStatement fillPreparedStatement(PreparedStatement pst) throws SQLException {
        pst = super.fillPreparedStatement(pst);
        pst.setString(5, this.journal);
        return pst;
    }

    @Override
    public PreparedStatement generateStatement(Connection connection) throws SQLException {
        PreparedStatement pst = connection.prepareStatement(statement);
        pst = this.fillPreparedStatement(pst);
        return pst;
    }

    public static void main(String[] args) {
        Article article = new Article();
        article.setKey("books/ph/Bach86");
        article.setTitle("");
        article.setDate("2017-05-31");
        article.setYear(1985);
        article.setJournal("dayone");

        PostgreSQL postgreSQL = new PostgreSQL();
        Connection conn = postgreSQL.getConnection();
        try{
            PreparedStatement pst = article.generateStatement(conn);
            System.out.println(pst.toString());
            pst.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
