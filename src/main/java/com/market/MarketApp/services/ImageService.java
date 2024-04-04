package com.market.MarketApp.services;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.market.MarketApp.models.ImageMeta;
import com.market.MarketApp.models.Person;
import com.market.MarketApp.models.Product;
import com.market.MarketApp.models.ProductImage;
import com.market.MarketApp.reposicories.ImageRepository;
import com.market.MarketApp.reposicories.ProductImageRepository;
import com.market.MarketApp.reposicories.ProductRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.Files;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ImageService {

    @Value("${spring.cloud.azure.storage.blob.container-name}")
    private String containerName;
    @Value("${azure.blob-storage.connection-string}")
    private String connection;

    private BlobServiceClient serviceClient;

    private final ImageRepository repository;
    private final ProductImageRepository productImageRepository;
    private final ProductRepository productRepository;
    @Autowired
    public ImageService(ImageRepository repository, ProductImageRepository productImageRepository, ProductRepository productRepository) {
        this.repository = repository;
        this.productImageRepository = productImageRepository;

        this.productRepository = productRepository;

    }
    @PostConstruct
    public void init(){
        serviceClient = new BlobServiceClientBuilder()
                .connectionString(connection).buildClient();
    }

    public String getImgageByPerson(Person person) throws IOException {

        Optional<ImageMeta> image = repository.findByOwner(person);

        if(image.isEmpty()) {
            File file = new File("src/main/resources/static/kartinki-znak-voprosa-39.jpg");
            MultipartFile file1 = getMultipartFile(file);
            return convertMultipartFileToBase64(file1);
        }
        ImageMeta imageMeta = image.get();



        MultipartFile file = getFileFromStorage(imageMeta.getFileName(), imageMeta.getContainerName());
        return convertMultipartFileToBase64(file);

    }


    public String getImageByProduct(Product product) throws IOException {
        Optional< ProductImage> image = productImageRepository.findByProduct(product);
        if(image.isEmpty()) {
            File file = new File("src/main/resources/static/kartinki-znak-voprosa-39.jpg");
            MultipartFile file1 = getMultipartFile(file);
            return convertMultipartFileToBase64(file1);
        }

        ProductImage productImage = image.get();
        MultipartFile file = getFileFromStorage(productImage.getFileName(), productImage.getContainerName());
        return convertMultipartFileToBase64(file);

    }



    @Transactional
    public void uploadFile(MultipartFile image, Person person) throws IOException {

        String fileName = person.getUsername() + person.getId() ;


        BlobClient client = serviceClient
                .getBlobContainerClient(containerName)
                .getBlobClient(fileName);

        client.upload(image.getInputStream(), image.getSize(), true);

        Optional<ImageMeta> metaOp = repository.findByOwner(person);

            if (metaOp.isPresent())
                repository.removeByOwner(person);

            ImageMeta meta  = new ImageMeta();
            meta.setFileName(fileName);
            meta.setFileFormat(image.getContentType());
            meta.setContainerName(containerName);
            meta.setOwner(person);
            person.setImageMeta(meta);
            repository.save(meta);



    }

    @Transactional
    public void uploadProductFile(int id, MultipartFile image) throws IOException {
        String fileName = "p" + id;

        BlobClient client = serviceClient
                .getBlobContainerClient("products")
                .getBlobClient(fileName);

        client.upload(image.getInputStream(), image.getSize(), true);

        Product product = productRepository.findById(id).get();

        Optional<ProductImage> imageProduct = productImageRepository.findByProduct(product);

        if(imageProduct.isPresent())
            productImageRepository.removeByProduct(product);

        ProductImage productImage = new ProductImage();

        productImage.setProduct(product);
        productImage.setFileName(fileName);
        productImage.setFileFormat(image.getContentType());
        productImage.setContainerName("products");
        productImageRepository.save(productImage);


    }
    private MultipartFile getFileFromStorage(String fileName, String containerName) {

        BlobClient client = serviceClient
                .getBlobContainerClient(containerName)
                .getBlobClient(fileName);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        client.download(outputStream);

        byte[] bytes = outputStream.toByteArray();


        MultipartFile file = new MultipartFile() {
            @Override
            public String getName() {
                return fileName;
            }

            @Override
            public String getOriginalFilename() {
                return fileName;
            }

            @Override
            public String getContentType() {
                return "image/jpeg";
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public long getSize() {
                return bytes.length;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return bytes;
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return new ByteArrayInputStream(bytes);
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {
                Files.write(dest.toPath(), bytes);
            }
        };
        return file;
    }
    private String convertMultipartFileToBase64(MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            return Base64.encodeBase64String(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static MultipartFile getMultipartFile(File file) throws IOException {
        FileInputStream inputStream = new FileInputStream(file);
        byte[] bytes = inputStream.readAllBytes();
        MultipartFile file1 =  new MultipartFile() {
            @Override
            public String getName() {
                return file.getName();
            }

            @Override
            public String getOriginalFilename() {
                return file.getName();
            }

            @Override
            public String getContentType() {
                return "image/jpeg";
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public long getSize() {
                return bytes.length;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return bytes;
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return new ByteArrayInputStream(bytes);
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {
                Files.write(dest.toPath(), bytes);
            }
        };
        return file1;
    }

}
