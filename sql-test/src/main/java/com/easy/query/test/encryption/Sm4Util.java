package com.easy.query.test.encryption;

import com.easy.query.core.exception.EasyQueryException;

import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * SM4(GB/T 32907-2016) 纯java实现 ECB模式 + PKCS7Padding,输出为16进制字符串
 * 不依赖任何第三方加密库(如BouncyCastle)
 * 分段加密场景下每个分段只有一个分组,ECB即可满足需求无需iv
 *
 * @author xuejiaming
 */
public class Sm4Util {

    private static final int BLOCK_SIZE = 16;

    private static final int[] SBOX = {
            0xd6, 0x90, 0xe9, 0xfe, 0xcc, 0xe1, 0x3d, 0xb7, 0x16, 0xb6, 0x14, 0xc2, 0x28, 0xfb, 0x2c, 0x05,
            0x2b, 0x67, 0x9a, 0x76, 0x2a, 0xbe, 0x04, 0xc3, 0xaa, 0x44, 0x13, 0x26, 0x49, 0x86, 0x06, 0x99,
            0x9c, 0x42, 0x50, 0xf4, 0x91, 0xef, 0x98, 0x7a, 0x33, 0x54, 0x0b, 0x43, 0xed, 0xcf, 0xac, 0x62,
            0xe4, 0xb3, 0x1c, 0xa9, 0xc9, 0x08, 0xe8, 0x95, 0x80, 0xdf, 0x94, 0xfa, 0x75, 0x8f, 0x3f, 0xa6,
            0x47, 0x07, 0xa7, 0xfc, 0xf3, 0x73, 0x17, 0xba, 0x83, 0x59, 0x3c, 0x19, 0xe6, 0x85, 0x4f, 0xa8,
            0x68, 0x6b, 0x81, 0xb2, 0x71, 0x64, 0xda, 0x8b, 0xf8, 0xeb, 0x0f, 0x4b, 0x70, 0x56, 0x9d, 0x35,
            0x1e, 0x24, 0x0e, 0x5e, 0x63, 0x58, 0xd1, 0xa2, 0x25, 0x22, 0x7c, 0x3b, 0x01, 0x21, 0x78, 0x87,
            0xd4, 0x00, 0x46, 0x57, 0x9f, 0xd3, 0x27, 0x52, 0x4c, 0x36, 0x02, 0xe7, 0xa0, 0xc4, 0xc8, 0x9e,
            0xea, 0xbf, 0x8a, 0xd2, 0x40, 0xc7, 0x38, 0xb5, 0xa3, 0xf7, 0xf2, 0xce, 0xf9, 0x61, 0x15, 0xa1,
            0xe0, 0xae, 0x5d, 0xa4, 0x9b, 0x34, 0x1a, 0x55, 0xad, 0x93, 0x32, 0x30, 0xf5, 0x8c, 0xb1, 0xe3,
            0x1d, 0xf6, 0xe2, 0x2e, 0x82, 0x66, 0xca, 0x60, 0xc0, 0x29, 0x23, 0xab, 0x0d, 0x53, 0x4e, 0x6f,
            0xd5, 0xdb, 0x37, 0x45, 0xde, 0xfd, 0x8e, 0x2f, 0x03, 0xff, 0x6a, 0x72, 0x6d, 0x6c, 0x5b, 0x51,
            0x8d, 0x1b, 0xaf, 0x92, 0xbb, 0xdd, 0xbc, 0x7f, 0x11, 0xd9, 0x5c, 0x41, 0x1f, 0x10, 0x5a, 0xd8,
            0x0a, 0xc1, 0x31, 0x88, 0xa5, 0xcd, 0x7b, 0xbd, 0x2d, 0x74, 0xd0, 0x12, 0xb8, 0xe5, 0xb4, 0xb0,
            0x89, 0x69, 0x97, 0x4a, 0x0c, 0x96, 0x77, 0x7e, 0x65, 0xb9, 0xf1, 0x09, 0xc5, 0x6e, 0xc6, 0x84,
            0x18, 0xf0, 0x7d, 0xec, 0x3a, 0xdc, 0x4d, 0x20, 0x79, 0xee, 0x5f, 0x3e, 0xd7, 0xcb, 0x39, 0x48
    };

    private static final int[] FK = {0xa3b1bac6, 0x56aa3350, 0x677d9197, 0xb27022dc};

    private static final int[] CK = {
            0x00070e15, 0x1c232a31, 0x383f464d, 0x545b6269,
            0x70777e85, 0x8c939aa1, 0xa8afb6bd, 0xc4cbd2d9,
            0xe0e7eef5, 0xfc030a11, 0x181f262d, 0x343b4249,
            0x50575e65, 0x6c737a81, 0x888f969d, 0xa4abb2b9,
            0xc0c7ced5, 0xdce3eaf1, 0xf8ff060d, 0x141b2229,
            0x30373e45, 0x4c535a61, 0x686f767d, 0x848b9299,
            0xa0a7aeb5, 0xbcc3cad1, 0xd8dfe6ed, 0xf4fb0209,
            0x10171e25, 0x2c333a41, 0x484f565d, 0x646b7279
    };

    private static final char[] HEX_CHARS = "0123456789abcdef".toCharArray();

    private static int rotl(int x, int n) {
        return (x << n) | (x >>> (32 - n));
    }

    private static int getInt(byte[] b, int offset) {
        return ((b[offset] & 0xff) << 24) | ((b[offset + 1] & 0xff) << 16) | ((b[offset + 2] & 0xff) << 8) | (b[offset + 3] & 0xff);
    }

    private static void putInt(int v, byte[] b, int offset) {
        b[offset] = (byte) (v >>> 24);
        b[offset + 1] = (byte) (v >>> 16);
        b[offset + 2] = (byte) (v >>> 8);
        b[offset + 3] = (byte) v;
    }

    /**
     * 非线性变换tau:对4个字节分别进行S盒替换
     */
    private static int tau(int a) {
        return (SBOX[(a >>> 24) & 0xff] << 24)
                | (SBOX[(a >>> 16) & 0xff] << 16)
                | (SBOX[(a >>> 8) & 0xff] << 8)
                | SBOX[a & 0xff];
    }

    /**
     * 加密线性变换L
     */
    private static int l(int b) {
        return b ^ rotl(b, 2) ^ rotl(b, 10) ^ rotl(b, 18) ^ rotl(b, 24);
    }

    /**
     * 密钥扩展线性变换L'
     */
    private static int lPrime(int b) {
        return b ^ rotl(b, 13) ^ rotl(b, 23);
    }

    private static int t(int x) {
        return l(tau(x));
    }

    private static int tPrime(int x) {
        return lPrime(tau(x));
    }

    /**
     * 密钥扩展生成32个轮密钥
     */
    private static int[] generateRoundKeys(byte[] key) {
        if (key == null || key.length != BLOCK_SIZE) {
            throw new EasyQueryException("sm4 key length must be 16 bytes");
        }
        int[] k = new int[36];
        for (int i = 0; i < 4; i++) {
            k[i] = getInt(key, i * 4) ^ FK[i];
        }
        int[] rk = new int[32];
        for (int i = 0; i < 32; i++) {
            k[i + 4] = k[i] ^ tPrime(k[i + 1] ^ k[i + 2] ^ k[i + 3] ^ CK[i]);
            rk[i] = k[i + 4];
        }
        return rk;
    }

    private static void cryptBlock(byte[] in, int inOffset, byte[] out, int outOffset, int[] rk, boolean decrypt) {
        int[] x = new int[36];
        for (int i = 0; i < 4; i++) {
            x[i] = getInt(in, inOffset + i * 4);
        }
        for (int i = 0; i < 32; i++) {
            x[i + 4] = x[i] ^ t(x[i + 1] ^ x[i + 2] ^ x[i + 3] ^ (decrypt ? rk[31 - i] : rk[i]));
        }
        //反序输出
        putInt(x[35], out, outOffset);
        putInt(x[34], out, outOffset + 4);
        putInt(x[33], out, outOffset + 8);
        putInt(x[32], out, outOffset + 12);
    }

    /**
     * ECB模式单块加密(16字节,不做填充),一般用于标准测试向量验证
     */
    public static byte[] encryptBlock(byte[] block, byte[] key) {
        if (block == null || block.length != BLOCK_SIZE) {
            throw new EasyQueryException("sm4 block length must be 16 bytes");
        }
        byte[] out = new byte[BLOCK_SIZE];
        cryptBlock(block, 0, out, 0, generateRoundKeys(key), false);
        return out;
    }

    public static String encrypt(String plaintext, String key, Charset charset) {
        byte[] encrypt = encrypt(plaintext.getBytes(charset), check16Bytes(key, charset, "key"));
        return toHex(encrypt);
    }

    public static byte[] encrypt(byte[] plaintext, byte[] key) {
        try {
            int[] rk = generateRoundKeys(key);
            //PKCS7Padding
            int padding = BLOCK_SIZE - (plaintext.length % BLOCK_SIZE);
            byte[] padded = Arrays.copyOf(plaintext, plaintext.length + padding);
            Arrays.fill(padded, plaintext.length, padded.length, (byte) padding);

            byte[] out = new byte[padded.length];
            for (int offset = 0; offset < padded.length; offset += BLOCK_SIZE) {
                cryptBlock(padded, offset, out, offset, rk, false);
            }
            return out;
        } catch (Exception e) {
            throw new EasyQueryException(e);
        }
    }

    public static String decrypt(String content, String key, Charset charset) {
        byte[] decrypt = decrypt(fromHex(content), check16Bytes(key, charset, "key"));
        return new String(decrypt, charset);
    }

    public static byte[] decrypt(byte[] encrypted, byte[] key) {
        try {
            if (encrypted == null || encrypted.length == 0 || encrypted.length % BLOCK_SIZE != 0) {
                throw new EasyQueryException("sm4 ciphertext length must be a multiple of 16");
            }
            int[] rk = generateRoundKeys(key);
            byte[] out = new byte[encrypted.length];
            for (int offset = 0; offset < encrypted.length; offset += BLOCK_SIZE) {
                cryptBlock(encrypted, offset, out, offset, rk, true);
            }
            //去除PKCS7Padding
            int padding = out[out.length - 1] & 0xff;
            if (padding < 1 || padding > BLOCK_SIZE) {
                throw new EasyQueryException("sm4 decrypt invalid padding");
            }
            return Arrays.copyOf(out, out.length - padding);
        } catch (EasyQueryException e) {
            throw e;
        } catch (Exception e) {
            throw new EasyQueryException(e);
        }
    }

    private static byte[] check16Bytes(String value, Charset charset, String name) {
        byte[] bytes = value.getBytes(charset);
        if (bytes.length != BLOCK_SIZE) {
            throw new EasyQueryException("sm4 " + name + " length must be 16 bytes");
        }
        return bytes;
    }

    public static String toHex(byte[] bytes) {
        char[] chars = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xff;
            chars[i * 2] = HEX_CHARS[v >>> 4];
            chars[i * 2 + 1] = HEX_CHARS[v & 0x0f];
        }
        return new String(chars);
    }

    public static byte[] fromHex(String hex) {
        int length = hex.length();
        if (length % 2 != 0) {
            throw new EasyQueryException("hex string length must be even");
        }
        byte[] bytes = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            int high = Character.digit(hex.charAt(i), 16);
            int low = Character.digit(hex.charAt(i + 1), 16);
            if (high < 0 || low < 0) {
                throw new EasyQueryException("invalid hex string:" + hex);
            }
            bytes[i / 2] = (byte) ((high << 4) | low);
        }
        return bytes;
    }
}
