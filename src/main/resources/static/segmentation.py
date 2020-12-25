import jieba


def stopword_list():
    stopwords = [line.strip() for line in open('stopword.txt', encoding='utf-8').readlines()]
    return stopwords


def seg_with_stop(sentence):
    sentence_seg = jieba.cut(sentence.strip())
    stopwords = stopword_list()
    out_string = ''
    
    for word in sentence_seg:
        if word not in stopwords:
            if word != '\t':
                out_string += word
                out_string += " "
    
    return out_string


def segmentation(sentence1):
    #print('len(sentence)',len(sentence))
    if len(sentence1)==0:
        return ''
    else:
        sentence = 'invalid '+sentence1
    sentence_seg = jieba.cut(sentence.strip())
    out_string = ' '
    for word in sentence_seg:
        out_string += word
        out_string += " "
    #print('out_string:',out_string)
    return out_string
