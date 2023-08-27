# Usage
alias mygit='java -jar /Users/tomonori.suzuki/mygit/target/mygit-1.0.jar'


## targetディレクトリを削除する
```shell
mvn clean
```

## パッケージ生成
```shell
mvn package
```

# mygit コマンド

## mygit init
git initをすると`.git`ディレクトリが作成される。

```console
.git/
├── HEAD
├── config
├── description
├── hooks/
│   ├── applypatch-msg.sample*
│   ├── commit-msg.sample*
│   ├── fsmonitor-watchman.sample*
│   ├── post-update.sample*
│   ├── pre-applypatch.sample*
│   ├── pre-commit.sample*
│   ├── pre-merge-commit.sample*
│   ├── pre-push.sample*
│   ├── pre-rebase.sample*
│   ├── pre-receive.sample*
│   ├── prepare-commit-msg.sample*
│   ├── push-to-checkout.sample*
│   └── update.sample*
├── info/
│   └── exclude
├── objects/
│   ├── info/
│   └── pack/
└── refs/
    ├── heads/
    └── tags/　
```

## mygit branch

## mygit checkout

## mygit add

## mygit commit

## mygit log

## mygit diff

## mygit status

# 参考サイト
* [公式ドキュメント](https://git-scm.com/book/ja/v2/Git%E3%81%AE%E5%86%85%E5%81%B4-Git%E3%82%AA%E3%83%96%E3%82%B8%E3%82%A7%E3%82%AF%E3%83%88)
* [Gitの作り方](https://engineering.mercari.com/blog/entry/2015-09-14-175300/)