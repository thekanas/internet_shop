package by.stolybko.servlet;

import by.stolybko.config.AppConfig;
import by.stolybko.service.ProductService;
import by.stolybko.service.dto.ProductRequestDto;
import by.stolybko.service.dto.ProductResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static by.stolybko.util.Constants.CONTENT_TYPE;

@WebServlet(name = "ProductServlet", value = "/products")
public class ProductServlet extends HttpServlet {

    private ProductService productService;

    @Override
    public void init(ServletConfig config) {
        this.productService = AppConfig.getProductService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            String id = req.getParameter("id");
            ObjectMapper objectMapper = new ObjectMapper();

            if (id == null) {

                List<ProductResponseDto> productResponseDtoList = productService.getAll();

                resp.setContentType(CONTENT_TYPE);
                resp.setStatus(200);

                objectMapper.writeValue(resp.getWriter(), productResponseDtoList);

            } else {

                ProductResponseDto productResponseDto = productService.getById(UUID.fromString(id));

                resp.setContentType(CONTENT_TYPE);
                resp.setStatus(200);

                objectMapper.writeValue(resp.getWriter(), productResponseDto);
            }

        } catch (Exception e) {
            resp.setStatus(400);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

            ObjectMapper objectMapper = new ObjectMapper();
            ProductRequestDto productRequestDto = objectMapper.readValue(req.getReader().lines().collect(Collectors.joining()), ProductRequestDto.class);
            ProductResponseDto productResponseDto = productService.save(productRequestDto);

            resp.setContentType(CONTENT_TYPE);
            resp.setStatus(200);

            objectMapper.writeValue(resp.getWriter(), productResponseDto);

        } catch (Exception e) {
            resp.setStatus(400);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            String id = req.getParameter("id");

            if (id == null) {
                resp.setStatus(400);
            } else {

                ObjectMapper objectMapper = new ObjectMapper();
                ProductRequestDto productRequestDto = objectMapper.readValue(req.getReader().lines().collect(Collectors.joining()), ProductRequestDto.class);
                ProductResponseDto productResponseDto = productService.update(UUID.fromString(id), productRequestDto);

                resp.setContentType(CONTENT_TYPE);
                resp.setStatus(200);

                objectMapper.writeValue(resp.getWriter(), productResponseDto);
            }

        } catch (Exception e) {
            resp.setStatus(400);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            String id = req.getParameter("id");

            if (id == null) {
                resp.setStatus(400);
            } else {

                boolean delete = productService.deleteById(UUID.fromString(id));

                if (delete) {
                    resp.setContentType(CONTENT_TYPE);
                    resp.setStatus(200);
                } else {
                    resp.setStatus(404);
                }
            }

        } catch (Exception e) {
            resp.setStatus(400);
        }
    }

}
