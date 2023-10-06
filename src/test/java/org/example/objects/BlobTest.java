package org.example.objects;

import org.example.filesystem.GitFileSystem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * ここでは、フォーマットのチェックさえできてれば、OKだと思う
*/
public class BlobTest {

    @Test
    @DisplayName("blobのフォーマットチェック")
    void blobFormatTest() throws Exception{
        Blob blob = new Blob("hello, world");
        String blobFormat = blob.asString();
        assertEquals(blobFormat, "blob 12\0hello, world");
    }

    @Test
    @DisplayName("blobのsha1テスト")
    void blobSHA1Test() throws Exception{
        Blob blob = new Blob("hello, world");
        // String sha1 = GitFileSystem.byte2string(blob.calcHash());
        String sha1 = GitFileSystem.byte2string(GitFileSystem.calcHash(blob.asBytes()));
        // 実際の値は、git hash-objectで取得した
        assertEquals(sha1, "8c01d89ae06311834ee4b1fab2f0414d35f01102");
    }

    @Test
    @DisplayName("blobのzlibテスト")
    void blobZlibTest() throws Exception{
        Blob blob = new Blob("hello, world");
        // 圧縮テスト
        // String compressData = GitFileSystem.byte2string(blob.applyZlib());
        byte[] compressByte = GitFileSystem.compress(blob.asBytes());
        String compressString = GitFileSystem.byte2string(compressByte);
        assertEquals(compressString, "78014bcac94f52303462c848cdc9c9d75128cf2fca49010042f306ab");
        // 解凍テスト
        byte[] decompressByte = GitFileSystem.decompress2Byte(compressByte);
        assertArrayEquals(decompressByte, blob.asBytes());
    }
}
